import { HttpResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit, inject, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { IAppuser } from 'app/entities/appuser/appuser.model';
import { AppuserService } from 'app/entities/appuser/service/appuser.service';
import { AlertError } from 'app/shared/alert/alert-error';
import { AlertErrorModel } from 'app/shared/alert/alert-error.model';
import { TranslateDirective } from 'app/shared/language';
import { IAppphoto } from '../appphoto.model';
import { AppphotoService } from '../service/appphoto.service';

import { AppphotoFormGroup, AppphotoFormService } from './appphoto-form.service';

@Component({
  selector: 'jhi-appphoto-update',
  templateUrl: './appphoto-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class AppphotoUpdate implements OnInit {
  readonly isSaving = signal(false);
  appphoto: IAppphoto | null = null;

  appusersCollection = signal<IAppuser[]>([]);

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected appphotoService = inject(AppphotoService);
  protected appphotoFormService = inject(AppphotoFormService);
  protected appuserService = inject(AppuserService);
  protected elementRef = inject(ElementRef);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AppphotoFormGroup = this.appphotoFormService.createAppphotoFormGroup();

  compareAppuser = (o1: IAppuser | null, o2: IAppuser | null): boolean => this.appuserService.compareAppuser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ appphoto }) => {
      this.appphoto = appphoto;
      if (appphoto) {
        this.updateForm(appphoto);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(
          new EventWithContent<AlertErrorModel>('opencodetestApp.error', { ...err, key: `error.file.${err.key}` }),
        ),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector(`#${idInput}`)) {
      this.elementRef.nativeElement.querySelector(`#${idInput}`).value = null;
    }
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const appphoto = this.appphotoFormService.getAppphoto(this.editForm);
    if (appphoto.id === null) {
      this.subscribeToSaveResponse(this.appphotoService.create(appphoto));
    } else {
      this.subscribeToSaveResponse(this.appphotoService.update(appphoto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<IAppphoto | null>): void {
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

  protected updateForm(appphoto: IAppphoto): void {
    this.appphoto = appphoto;
    this.appphotoFormService.resetForm(this.editForm, appphoto);

    this.appusersCollection.set(this.appuserService.addAppuserToCollectionIfMissing<IAppuser>(this.appusersCollection(), appphoto.appuser));
  }

  protected loadRelationshipsOptions(): void {
    this.appuserService
      .query({ 'appphotoId.specified': 'false' })
      .pipe(map((res: HttpResponse<IAppuser[]>) => res.body ?? []))
      .pipe(map((appusers: IAppuser[]) => this.appuserService.addAppuserToCollectionIfMissing<IAppuser>(appusers, this.appphoto?.appuser)))
      .subscribe((appusers: IAppuser[]) => this.appusersCollection.set(appusers));
  }
}
