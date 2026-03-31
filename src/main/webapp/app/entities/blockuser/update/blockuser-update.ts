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
import { ICommunity } from 'app/entities/community/community.model';
import { CommunityService } from 'app/entities/community/service/community.service';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { IBlockuser } from '../blockuser.model';
import { BlockuserService } from '../service/blockuser.service';

import { BlockuserFormGroup, BlockuserFormService } from './blockuser-form.service';

@Component({
  selector: 'jhi-blockuser-update',
  templateUrl: './blockuser-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class BlockuserUpdate implements OnInit {
  readonly isSaving = signal(false);
  blockuser: IBlockuser | null = null;

  appusersSharedCollection = signal<IAppuser[]>([]);
  communitiesSharedCollection = signal<ICommunity[]>([]);

  protected blockuserService = inject(BlockuserService);
  protected blockuserFormService = inject(BlockuserFormService);
  protected appuserService = inject(AppuserService);
  protected communityService = inject(CommunityService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: BlockuserFormGroup = this.blockuserFormService.createBlockuserFormGroup();

  compareAppuser = (o1: IAppuser | null, o2: IAppuser | null): boolean => this.appuserService.compareAppuser(o1, o2);

  compareCommunity = (o1: ICommunity | null, o2: ICommunity | null): boolean => this.communityService.compareCommunity(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ blockuser }) => {
      this.blockuser = blockuser;
      if (blockuser) {
        this.updateForm(blockuser);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const blockuser = this.blockuserFormService.getBlockuser(this.editForm);
    if (blockuser.id === null) {
      this.subscribeToSaveResponse(this.blockuserService.create(blockuser));
    } else {
      this.subscribeToSaveResponse(this.blockuserService.update(blockuser));
    }
  }

  protected subscribeToSaveResponse(result: Observable<IBlockuser | null>): void {
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

  protected updateForm(blockuser: IBlockuser): void {
    this.blockuser = blockuser;
    this.blockuserFormService.resetForm(this.editForm, blockuser);

    this.appusersSharedCollection.update(appusers =>
      this.appuserService.addAppuserToCollectionIfMissing<IAppuser>(appusers, blockuser.blockeduser, blockuser.blockinguser),
    );
    this.communitiesSharedCollection.update(communities =>
      this.communityService.addCommunityToCollectionIfMissing<ICommunity>(communities, blockuser.cblockeduser, blockuser.cblockinguser),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.appuserService
      .query()
      .pipe(map((res: HttpResponse<IAppuser[]>) => res.body ?? []))
      .pipe(
        map((appusers: IAppuser[]) =>
          this.appuserService.addAppuserToCollectionIfMissing<IAppuser>(
            appusers,
            this.blockuser?.blockeduser,
            this.blockuser?.blockinguser,
          ),
        ),
      )
      .subscribe((appusers: IAppuser[]) => this.appusersSharedCollection.set(appusers));

    this.communityService
      .query()
      .pipe(map((res: HttpResponse<ICommunity[]>) => res.body ?? []))
      .pipe(
        map((communities: ICommunity[]) =>
          this.communityService.addCommunityToCollectionIfMissing<ICommunity>(
            communities,
            this.blockuser?.cblockeduser,
            this.blockuser?.cblockinguser,
          ),
        ),
      )
      .subscribe((communities: ICommunity[]) => this.communitiesSharedCollection.set(communities));
  }
}
