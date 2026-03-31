import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IConfigVariables } from '../config-variables.model';
import { ConfigVariablesService } from '../service/config-variables.service';

import { ConfigVariablesFormService } from './config-variables-form.service';
import { ConfigVariablesUpdate } from './config-variables-update';

describe('ConfigVariables Management Update Component', () => {
  let comp: ConfigVariablesUpdate;
  let fixture: ComponentFixture<ConfigVariablesUpdate>;
  let activatedRoute: ActivatedRoute;
  let configVariablesFormService: ConfigVariablesFormService;
  let configVariablesService: ConfigVariablesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [
        provideHttpClientTesting(),
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    });

    fixture = TestBed.createComponent(ConfigVariablesUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    configVariablesFormService = TestBed.inject(ConfigVariablesFormService);
    configVariablesService = TestBed.inject(ConfigVariablesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const configVariables: IConfigVariables = { id: 5001 };

      activatedRoute.data = of({ configVariables });
      comp.ngOnInit();

      expect(comp.configVariables).toEqual(configVariables);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<IConfigVariables>();
      const configVariables = { id: 13986 };
      vitest.spyOn(configVariablesFormService, 'getConfigVariables').mockReturnValue(configVariables);
      vitest.spyOn(configVariablesService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ configVariables });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(configVariables);
      saveSubject.complete();

      // THEN
      expect(configVariablesFormService.getConfigVariables).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(configVariablesService.update).toHaveBeenCalledWith(expect.objectContaining(configVariables));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<IConfigVariables>();
      const configVariables = { id: 13986 };
      vitest.spyOn(configVariablesFormService, 'getConfigVariables').mockReturnValue({ id: null });
      vitest.spyOn(configVariablesService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ configVariables: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(configVariables);
      saveSubject.complete();

      // THEN
      expect(configVariablesFormService.getConfigVariables).toHaveBeenCalled();
      expect(configVariablesService.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<IConfigVariables>();
      const configVariables = { id: 13986 };
      vitest.spyOn(configVariablesService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ configVariables });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(configVariablesService.update).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
