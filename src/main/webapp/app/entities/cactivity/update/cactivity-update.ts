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
import { ICactivity } from '../cactivity.model';
import { CactivityService } from '../service/cactivity.service';

import { CactivityFormGroup, CactivityFormService } from './cactivity-form.service';

@Component({
  selector: 'jhi-cactivity-update',
  templateUrl: './cactivity-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class CactivityUpdate implements OnInit {
  readonly isSaving = signal(false);
  cactivity: ICactivity | null = null;

  communitiesSharedCollection = signal<ICommunity[]>([]);

  protected cactivityService = inject(CactivityService);
  protected cactivityFormService = inject(CactivityFormService);
  protected communityService = inject(CommunityService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CactivityFormGroup = this.cactivityFormService.createCactivityFormGroup();

  compareCommunity = (o1: ICommunity | null, o2: ICommunity | null): boolean => this.communityService.compareCommunity(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cactivity }) => {
      this.cactivity = cactivity;
      if (cactivity) {
        this.updateForm(cactivity);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const cactivity = this.cactivityFormService.getCactivity(this.editForm);
    if (cactivity.id === null) {
      this.subscribeToSaveResponse(this.cactivityService.create(cactivity));
    } else {
      this.subscribeToSaveResponse(this.cactivityService.update(cactivity));
    }
  }

  protected subscribeToSaveResponse(result: Observable<ICactivity | null>): void {
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

  protected updateForm(cactivity: ICactivity): void {
    this.cactivity = cactivity;
    this.cactivityFormService.resetForm(this.editForm, cactivity);

    this.communitiesSharedCollection.update(communities =>
      this.communityService.addCommunityToCollectionIfMissing<ICommunity>(communities, ...(cactivity.communities ?? [])),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.communityService
      .query()
      .pipe(map((res: HttpResponse<ICommunity[]>) => res.body ?? []))
      .pipe(
        map((communities: ICommunity[]) =>
          this.communityService.addCommunityToCollectionIfMissing<ICommunity>(communities, ...(this.cactivity?.communities ?? [])),
        ),
      )
      .subscribe((communities: ICommunity[]) => this.communitiesSharedCollection.set(communities));
  }
}
