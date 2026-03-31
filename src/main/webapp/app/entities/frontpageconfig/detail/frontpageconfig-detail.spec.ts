import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';

import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { faArrowLeft, faPencilAlt } from '@fortawesome/free-solid-svg-icons';
import { TranslateModule } from '@ngx-translate/core';
import { of } from 'rxjs';

import { FrontpageconfigDetail } from './frontpageconfig-detail';

describe('Frontpageconfig Management Detail Component', () => {
  let comp: FrontpageconfigDetail;
  let fixture: ComponentFixture<FrontpageconfigDetail>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./frontpageconfig-detail').then(m => m.FrontpageconfigDetail),
              resolve: { frontpageconfig: () => of({ id: 17235 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    });
    const library = TestBed.inject(FaIconLibrary);
    library.addIcons(faArrowLeft);
    library.addIcons(faPencilAlt);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FrontpageconfigDetail);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load frontpageconfig on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FrontpageconfigDetail);

      // THEN
      expect(instance.frontpageconfig()).toEqual(expect.objectContaining({ id: 17235 }));
    });
  });

  describe('PreviousState', () => {
    it('should navigate to previous state', () => {
      vitest.spyOn(globalThis.history, 'back');
      comp.previousState();
      expect(globalThis.history.back).toHaveBeenCalled();
    });
  });
});
