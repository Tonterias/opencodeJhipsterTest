import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAppuser } from 'app/entities/appuser/appuser.model';
import { AppuserService } from 'app/entities/appuser/service/appuser.service';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { ICeleb } from '../celeb.model';
import { CelebService } from '../service/celeb.service';

import { CelebFormGroup, CelebFormService } from './celeb-form.service';

@Component({
  selector: 'jhi-celeb-update',
  templateUrl: './celeb-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class CelebUpdate implements OnInit {
  readonly isSaving = signal(false);
  celeb: ICeleb | null = null;

  appusersSharedCollection = signal<IAppuser[]>([]);

  protected celebService = inject(CelebService);
  protected celebFormService = inject(CelebFormService);
  protected appuserService = inject(AppuserService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CelebFormGroup = this.celebFormService.createCelebFormGroup();

  compareAppuser = (o1: IAppuser | null, o2: IAppuser | null): boolean => this.appuserService.compareAppuser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ celeb }) => {
      this.celeb = celeb;
      if (celeb) {
        this.updateForm(celeb);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const celeb = this.celebFormService.getCeleb(this.editForm);
    if (celeb.id === null) {
      this.subscribeToSaveResponse(this.celebService.create(celeb));
    } else {
      this.subscribeToSaveResponse(this.celebService.update(celeb));
    }
  }

  protected subscribeToSaveResponse(result: Observable<ICeleb | null>): void {
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

  protected updateForm(celeb: ICeleb): void {
    this.celeb = celeb;
    this.celebFormService.resetForm(this.editForm, celeb);

    this.appusersSharedCollection.update(appusers =>
      this.appuserService.addAppuserToCollectionIfMissing<IAppuser>(appusers, ...(celeb.appusers ?? [])),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.appuserService
      .query()
      .pipe(map((res: HttpResponse<IAppuser[]>) => res.body ?? []))
      .pipe(
        map((appusers: IAppuser[]) =>
          this.appuserService.addAppuserToCollectionIfMissing<IAppuser>(appusers, ...(this.celeb?.appusers ?? [])),
        ),
      )
      .subscribe((appusers: IAppuser[]) => this.appusersSharedCollection.set(appusers));
  }
}
