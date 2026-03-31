import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IComment, NewComment } from '../comment.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IComment for edit and NewCommentFormGroupInput for create.
 */
type CommentFormGroupInput = IComment | PartialWithRequiredKeyOf<NewComment>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IComment | NewComment> = Omit<T, 'creationDate'> & {
  creationDate?: string | null;
};

type CommentFormRawValue = FormValueOf<IComment>;

type NewCommentFormRawValue = FormValueOf<NewComment>;

type CommentFormDefaults = Pick<NewComment, 'id' | 'creationDate' | 'isOffensive'>;

type CommentFormGroupContent = {
  id: FormControl<CommentFormRawValue['id'] | NewComment['id']>;
  creationDate: FormControl<CommentFormRawValue['creationDate']>;
  commentText: FormControl<CommentFormRawValue['commentText']>;
  isOffensive: FormControl<CommentFormRawValue['isOffensive']>;
  appuser: FormControl<CommentFormRawValue['appuser']>;
  post: FormControl<CommentFormRawValue['post']>;
};

export type CommentFormGroup = FormGroup<CommentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CommentFormService {
  createCommentFormGroup(comment?: CommentFormGroupInput): CommentFormGroup {
    const commentRawValue = this.convertCommentToCommentRawValue({
      ...this.getFormDefaults(),
      ...(comment ?? { id: null }),
    });
    return new FormGroup<CommentFormGroupContent>({
      id: new FormControl(
        { value: commentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      creationDate: new FormControl(commentRawValue.creationDate, {
        validators: [Validators.required],
      }),
      commentText: new FormControl(commentRawValue.commentText, {
        validators: [Validators.required, Validators.minLength(2), Validators.maxLength(65000)],
      }),
      isOffensive: new FormControl(commentRawValue.isOffensive),
      appuser: new FormControl(commentRawValue.appuser, {
        validators: [Validators.required],
      }),
      post: new FormControl(commentRawValue.post, {
        validators: [Validators.required],
      }),
    });
  }

  getComment(form: CommentFormGroup): IComment | NewComment {
    return this.convertCommentRawValueToComment(form.getRawValue() as CommentFormRawValue | NewCommentFormRawValue);
  }

  resetForm(form: CommentFormGroup, comment: CommentFormGroupInput): void {
    const commentRawValue = this.convertCommentToCommentRawValue({ ...this.getFormDefaults(), ...comment });
    form.reset({
      ...commentRawValue,
      id: { value: commentRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): CommentFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      creationDate: currentTime,
      isOffensive: false,
    };
  }

  private convertCommentRawValueToComment(rawComment: CommentFormRawValue | NewCommentFormRawValue): IComment | NewComment {
    return {
      ...rawComment,
      creationDate: dayjs(rawComment.creationDate, DATE_TIME_FORMAT),
    };
  }

  private convertCommentToCommentRawValue(
    comment: IComment | (Partial<NewComment> & CommentFormDefaults),
  ): CommentFormRawValue | PartialWithRequiredKeyOf<NewCommentFormRawValue> {
    return {
      ...comment,
      creationDate: comment.creationDate ? comment.creationDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
