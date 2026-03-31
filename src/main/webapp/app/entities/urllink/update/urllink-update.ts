import { Component, OnInit, inject, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { UrllinkService } from '../service/urllink.service';
import { IUrllink } from '../urllink.model';

import { UrllinkFormGroup, UrllinkFormService } from './urllink-form.service';

@Component({
  selector: 'jhi-urllink-update',
  templateUrl: './urllink-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class UrllinkUpdate implements OnInit {
  readonly isSaving = signal(false);
  urllink: IUrllink | null = null;

  protected urllinkService = inject(UrllinkService);
  protected urllinkFormService = inject(UrllinkFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: UrllinkFormGroup = this.urllinkFormService.createUrllinkFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ urllink }) => {
      this.urllink = urllink;
      if (urllink) {
        this.updateForm(urllink);
      }
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const urllink = this.urllinkFormService.getUrllink(this.editForm);
    if (urllink.id === null) {
      this.subscribeToSaveResponse(this.urllinkService.create(urllink));
    } else {
      this.subscribeToSaveResponse(this.urllinkService.update(urllink));
    }
  }

  protected subscribeToSaveResponse(result: Observable<IUrllink | null>): void {
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

  protected updateForm(urllink: IUrllink): void {
    this.urllink = urllink;
    this.urllinkFormService.resetForm(this.editForm, urllink);
  }
}
