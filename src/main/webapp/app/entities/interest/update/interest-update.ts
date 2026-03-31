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
import { IInterest } from '../interest.model';
import { InterestService } from '../service/interest.service';

import { InterestFormGroup, InterestFormService } from './interest-form.service';

@Component({
  selector: 'jhi-interest-update',
  templateUrl: './interest-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class InterestUpdate implements OnInit {
  readonly isSaving = signal(false);
  interest: IInterest | null = null;

  appusersSharedCollection = signal<IAppuser[]>([]);

  protected interestService = inject(InterestService);
  protected interestFormService = inject(InterestFormService);
  protected appuserService = inject(AppuserService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: InterestFormGroup = this.interestFormService.createInterestFormGroup();

  compareAppuser = (o1: IAppuser | null, o2: IAppuser | null): boolean => this.appuserService.compareAppuser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ interest }) => {
      this.interest = interest;
      if (interest) {
        this.updateForm(interest);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const interest = this.interestFormService.getInterest(this.editForm);
    if (interest.id === null) {
      this.subscribeToSaveResponse(this.interestService.create(interest));
    } else {
      this.subscribeToSaveResponse(this.interestService.update(interest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<IInterest | null>): void {
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

  protected updateForm(interest: IInterest): void {
    this.interest = interest;
    this.interestFormService.resetForm(this.editForm, interest);

    this.appusersSharedCollection.update(appusers =>
      this.appuserService.addAppuserToCollectionIfMissing<IAppuser>(appusers, ...(interest.appusers ?? [])),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.appuserService
      .query()
      .pipe(map((res: HttpResponse<IAppuser[]>) => res.body ?? []))
      .pipe(
        map((appusers: IAppuser[]) =>
          this.appuserService.addAppuserToCollectionIfMissing<IAppuser>(appusers, ...(this.interest?.appusers ?? [])),
        ),
      )
      .subscribe((appusers: IAppuser[]) => this.appusersSharedCollection.set(appusers));
  }
}
