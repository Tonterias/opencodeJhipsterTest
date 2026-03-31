import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPost, NewPost } from '../post.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPost for edit and NewPostFormGroupInput for create.
 */
type PostFormGroupInput = IPost | PartialWithRequiredKeyOf<NewPost>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPost | NewPost> = Omit<T, 'creationDate' | 'publicationDate'> & {
  creationDate?: string | null;
  publicationDate?: string | null;
};

type PostFormRawValue = FormValueOf<IPost>;

type NewPostFormRawValue = FormValueOf<NewPost>;

type PostFormDefaults = Pick<NewPost, 'id' | 'creationDate' | 'publicationDate' | 'tags' | 'topics'>;

type PostFormGroupContent = {
  id: FormControl<PostFormRawValue['id'] | NewPost['id']>;
  creationDate: FormControl<PostFormRawValue['creationDate']>;
  publicationDate: FormControl<PostFormRawValue['publicationDate']>;
  headline: FormControl<PostFormRawValue['headline']>;
  leadtext: FormControl<PostFormRawValue['leadtext']>;
  bodytext: FormControl<PostFormRawValue['bodytext']>;
  quote: FormControl<PostFormRawValue['quote']>;
  conclusion: FormControl<PostFormRawValue['conclusion']>;
  linkText: FormControl<PostFormRawValue['linkText']>;
  linkURL: FormControl<PostFormRawValue['linkURL']>;
  image: FormControl<PostFormRawValue['image']>;
  imageContentType: FormControl<PostFormRawValue['imageContentType']>;
  appuser: FormControl<PostFormRawValue['appuser']>;
  blog: FormControl<PostFormRawValue['blog']>;
  tags: FormControl<PostFormRawValue['tags']>;
  topics: FormControl<PostFormRawValue['topics']>;
};

export type PostFormGroup = FormGroup<PostFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PostFormService {
  createPostFormGroup(post?: PostFormGroupInput): PostFormGroup {
    const postRawValue = this.convertPostToPostRawValue({
      ...this.getFormDefaults(),
      ...(post ?? { id: null }),
    });
    return new FormGroup<PostFormGroupContent>({
      id: new FormControl(
        { value: postRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      creationDate: new FormControl(postRawValue.creationDate, {
        validators: [Validators.required],
      }),
      publicationDate: new FormControl(postRawValue.publicationDate),
      headline: new FormControl(postRawValue.headline, {
        validators: [Validators.required, Validators.minLength(2), Validators.maxLength(100)],
      }),
      leadtext: new FormControl(postRawValue.leadtext, {
        validators: [Validators.minLength(2), Validators.maxLength(1000)],
      }),
      bodytext: new FormControl(postRawValue.bodytext, {
        validators: [Validators.required, Validators.minLength(2), Validators.maxLength(65000)],
      }),
      quote: new FormControl(postRawValue.quote, {
        validators: [Validators.minLength(2), Validators.maxLength(1000)],
      }),
      conclusion: new FormControl(postRawValue.conclusion, {
        validators: [Validators.minLength(2), Validators.maxLength(2000)],
      }),
      linkText: new FormControl(postRawValue.linkText, {
        validators: [Validators.minLength(2), Validators.maxLength(1000)],
      }),
      linkURL: new FormControl(postRawValue.linkURL, {
        validators: [Validators.minLength(2), Validators.maxLength(1000)],
      }),
      image: new FormControl(postRawValue.image),
      imageContentType: new FormControl(postRawValue.imageContentType),
      appuser: new FormControl(postRawValue.appuser, {
        validators: [Validators.required],
      }),
      blog: new FormControl(postRawValue.blog, {
        validators: [Validators.required],
      }),
      tags: new FormControl(postRawValue.tags ?? []),
      topics: new FormControl(postRawValue.topics ?? []),
    });
  }

  getPost(form: PostFormGroup): IPost | NewPost {
    return this.convertPostRawValueToPost(form.getRawValue() as PostFormRawValue | NewPostFormRawValue);
  }

  resetForm(form: PostFormGroup, post: PostFormGroupInput): void {
    const postRawValue = this.convertPostToPostRawValue({ ...this.getFormDefaults(), ...post });
    form.reset({
      ...postRawValue,
      id: { value: postRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): PostFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      creationDate: currentTime,
      publicationDate: currentTime,
      tags: [],
      topics: [],
    };
  }

  private convertPostRawValueToPost(rawPost: PostFormRawValue | NewPostFormRawValue): IPost | NewPost {
    return {
      ...rawPost,
      creationDate: dayjs(rawPost.creationDate, DATE_TIME_FORMAT),
      publicationDate: dayjs(rawPost.publicationDate, DATE_TIME_FORMAT),
    };
  }

  private convertPostToPostRawValue(
    post: IPost | (Partial<NewPost> & PostFormDefaults),
  ): PostFormRawValue | PartialWithRequiredKeyOf<NewPostFormRawValue> {
    return {
      ...post,
      creationDate: post.creationDate ? post.creationDate.format(DATE_TIME_FORMAT) : undefined,
      publicationDate: post.publicationDate ? post.publicationDate.format(DATE_TIME_FORMAT) : undefined,
      tags: post.tags ?? [],
      topics: post.topics ?? [],
    };
  }
}
