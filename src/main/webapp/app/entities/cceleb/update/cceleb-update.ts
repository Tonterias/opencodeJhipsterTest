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
import { ICceleb } from '../cceleb.model';
import { CcelebService } from '../service/cceleb.service';

import { CcelebFormGroup, CcelebFormService } from './cceleb-form.service';

@Component({
  selector: 'jhi-cceleb-update',
  templateUrl: './cceleb-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class CcelebUpdate implements OnInit {
  readonly isSaving = signal(false);
  cceleb: ICceleb | null = null;

  communitiesSharedCollection = signal<ICommunity[]>([]);

  protected ccelebService = inject(CcelebService);
  protected ccelebFormService = inject(CcelebFormService);
  protected communityService = inject(CommunityService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CcelebFormGroup = this.ccelebFormService.createCcelebFormGroup();

  compareCommunity = (o1: ICommunity | null, o2: ICommunity | null): boolean => this.communityService.compareCommunity(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cceleb }) => {
      this.cceleb = cceleb;
      if (cceleb) {
        this.updateForm(cceleb);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const cceleb = this.ccelebFormService.getCceleb(this.editForm);
    if (cceleb.id === null) {
      this.subscribeToSaveResponse(this.ccelebService.create(cceleb));
    } else {
      this.subscribeToSaveResponse(this.ccelebService.update(cceleb));
    }
  }

  protected subscribeToSaveResponse(result: Observable<ICceleb | null>): void {
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

  protected updateForm(cceleb: ICceleb): void {
    this.cceleb = cceleb;
    this.ccelebFormService.resetForm(this.editForm, cceleb);

    this.communitiesSharedCollection.update(communities =>
      this.communityService.addCommunityToCollectionIfMissing<ICommunity>(communities, ...(cceleb.communities ?? [])),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.communityService
      .query()
      .pipe(map((res: HttpResponse<ICommunity[]>) => res.body ?? []))
      .pipe(
        map((communities: ICommunity[]) =>
          this.communityService.addCommunityToCollectionIfMissing<ICommunity>(communities, ...(this.cceleb?.communities ?? [])),
        ),
      )
      .subscribe((communities: ICommunity[]) => this.communitiesSharedCollection.set(communities));
  }
}
