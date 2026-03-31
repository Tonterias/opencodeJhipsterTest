import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICommunity } from 'app/entities/community/community.model';
import { CommunityService } from 'app/entities/community/service/community.service';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { ICinterest } from '../cinterest.model';
import { CinterestService } from '../service/cinterest.service';

import { CinterestFormGroup, CinterestFormService } from './cinterest-form.service';

@Component({
  selector: 'jhi-cinterest-update',
  templateUrl: './cinterest-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class CinterestUpdate implements OnInit {
  readonly isSaving = signal(false);
  cinterest: ICinterest | null = null;

  communitiesSharedCollection = signal<ICommunity[]>([]);

  protected cinterestService = inject(CinterestService);
  protected cinterestFormService = inject(CinterestFormService);
  protected communityService = inject(CommunityService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CinterestFormGroup = this.cinterestFormService.createCinterestFormGroup();

  compareCommunity = (o1: ICommunity | null, o2: ICommunity | null): boolean => this.communityService.compareCommunity(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cinterest }) => {
      this.cinterest = cinterest;
      if (cinterest) {
        this.updateForm(cinterest);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const cinterest = this.cinterestFormService.getCinterest(this.editForm);
    if (cinterest.id === null) {
      this.subscribeToSaveResponse(this.cinterestService.create(cinterest));
    } else {
      this.subscribeToSaveResponse(this.cinterestService.update(cinterest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<ICinterest | null>): void {
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

  protected updateForm(cinterest: ICinterest): void {
    this.cinterest = cinterest;
    this.cinterestFormService.resetForm(this.editForm, cinterest);

    this.communitiesSharedCollection.update(communities =>
      this.communityService.addCommunityToCollectionIfMissing<ICommunity>(communities, ...(cinterest.communities ?? [])),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.communityService
      .query()
      .pipe(map((res: HttpResponse<ICommunity[]>) => res.body ?? []))
      .pipe(
        map((communities: ICommunity[]) =>
          this.communityService.addCommunityToCollectionIfMissing<ICommunity>(communities, ...(this.cinterest?.communities ?? [])),
        ),
      )
      .subscribe((communities: ICommunity[]) => this.communitiesSharedCollection.set(communities));
  }
}
