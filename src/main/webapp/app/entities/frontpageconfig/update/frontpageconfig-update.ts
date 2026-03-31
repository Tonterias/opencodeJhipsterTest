import { Component, OnInit, inject, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { IFrontpageconfig } from '../frontpageconfig.model';
import { FrontpageconfigService } from '../service/frontpageconfig.service';

import { FrontpageconfigFormGroup, FrontpageconfigFormService } from './frontpageconfig-form.service';

@Component({
  selector: 'jhi-frontpageconfig-update',
  templateUrl: './frontpageconfig-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class FrontpageconfigUpdate implements OnInit {
  readonly isSaving = signal(false);
  frontpageconfig: IFrontpageconfig | null = null;

  protected frontpageconfigService = inject(FrontpageconfigService);
  protected frontpageconfigFormService = inject(FrontpageconfigFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FrontpageconfigFormGroup = this.frontpageconfigFormService.createFrontpageconfigFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ frontpageconfig }) => {
      this.frontpageconfig = frontpageconfig;
      if (frontpageconfig) {
        this.updateForm(frontpageconfig);
      }
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const frontpageconfig = this.frontpageconfigFormService.getFrontpageconfig(this.editForm);
    if (frontpageconfig.id === null) {
      this.subscribeToSaveResponse(this.frontpageconfigService.create(frontpageconfig));
    } else {
      this.subscribeToSaveResponse(this.frontpageconfigService.update(frontpageconfig));
    }
  }

  protected subscribeToSaveResponse(result: Observable<IFrontpageconfig | null>): void {
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

  protected updateForm(frontpageconfig: IFrontpageconfig): void {
    this.frontpageconfig = frontpageconfig;
    this.frontpageconfigFormService.resetForm(this.editForm, frontpageconfig);
  }
}
