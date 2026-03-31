import { Component, OnInit, inject, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { IConfigVariables } from '../config-variables.model';
import { ConfigVariablesService } from '../service/config-variables.service';

import { ConfigVariablesFormGroup, ConfigVariablesFormService } from './config-variables-form.service';

@Component({
  selector: 'jhi-config-variables-update',
  templateUrl: './config-variables-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class ConfigVariablesUpdate implements OnInit {
  readonly isSaving = signal(false);
  configVariables: IConfigVariables | null = null;

  protected configVariablesService = inject(ConfigVariablesService);
  protected configVariablesFormService = inject(ConfigVariablesFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ConfigVariablesFormGroup = this.configVariablesFormService.createConfigVariablesFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ configVariables }) => {
      this.configVariables = configVariables;
      if (configVariables) {
        this.updateForm(configVariables);
      }
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const configVariables = this.configVariablesFormService.getConfigVariables(this.editForm);
    if (configVariables.id === null) {
      this.subscribeToSaveResponse(this.configVariablesService.create(configVariables));
    } else {
      this.subscribeToSaveResponse(this.configVariablesService.update(configVariables));
    }
  }

  protected subscribeToSaveResponse(result: Observable<IConfigVariables | null>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving.set(false);
  }

  protected updateForm(configVariables: IConfigVariables): void {
    this.configVariables = configVariables;
    this.configVariablesFormService.resetForm(this.editForm, configVariables);
  }
}
