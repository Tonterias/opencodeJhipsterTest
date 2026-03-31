import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IBlog, NewBlog } from '../blog.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBlog for edit and NewBlogFormGroupInput for create.
 */
type BlogFormGroupInput = IBlog | PartialWithRequiredKeyOf<NewBlog>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IBlog | NewBlog> = Omit<T, 'creationDate'> & {
  creationDate?: string | null;
};

type BlogFormRawValue = FormValueOf<IBlog>;

type NewBlogFormRawValue = FormValueOf<NewBlog>;

type BlogFormDefaults = Pick<NewBlog, 'id' | 'creationDate'>;

type BlogFormGroupContent = {
  id: FormControl<BlogFormRawValue['id'] | NewBlog['id']>;
  creationDate: FormControl<BlogFormRawValue['creationDate']>;
  title: FormControl<BlogFormRawValue['title']>;
  image: FormControl<BlogFormRawValue['image']>;
  imageContentType: FormControl<BlogFormRawValue['imageContentType']>;
  appuser: FormControl<BlogFormRawValue['appuser']>;
  community: FormControl<BlogFormRawValue['community']>;
};

export type BlogFormGroup = FormGroup<BlogFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BlogFormService {
  createBlogFormGroup(blog?: BlogFormGroupInput): BlogFormGroup {
    const blogRawValue = this.convertBlogToBlogRawValue({
      ...this.getFormDefaults(),
      ...(blog ?? { id: null }),
    });
    return new FormGroup<BlogFormGroupContent>({
      id: new FormControl(
        { value: blogRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      creationDate: new FormControl(blogRawValue.creationDate, {
        validators: [Validators.required],
      }),
      title: new FormControl(blogRawValue.title, {
        validators: [Validators.required, Validators.minLength(2), Validators.maxLength(100)],
      }),
      image: new FormControl(blogRawValue.image),
      imageContentType: new FormControl(blogRawValue.imageContentType),
      appuser: new FormControl(blogRawValue.appuser, {
        validators: [Validators.required],
      }),
      community: new FormControl(blogRawValue.community),
    });
  }

  getBlog(form: BlogFormGroup): IBlog | NewBlog {
    return this.convertBlogRawValueToBlog(form.getRawValue() as BlogFormRawValue | NewBlogFormRawValue);
  }

  resetForm(form: BlogFormGroup, blog: BlogFormGroupInput): void {
    const blogRawValue = this.convertBlogToBlogRawValue({ ...this.getFormDefaults(), ...blog });
    form.reset({
      ...blogRawValue,
      id: { value: blogRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): BlogFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      creationDate: currentTime,
    };
  }

  private convertBlogRawValueToBlog(rawBlog: BlogFormRawValue | NewBlogFormRawValue): IBlog | NewBlog {
    return {
      ...rawBlog,
      creationDate: dayjs(rawBlog.creationDate, DATE_TIME_FORMAT),
    };
  }

  private convertBlogToBlogRawValue(
    blog: IBlog | (Partial<NewBlog> & BlogFormDefaults),
  ): BlogFormRawValue | PartialWithRequiredKeyOf<NewBlogFormRawValue> {
    return {
      ...blog,
      creationDate: blog.creationDate ? blog.creationDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
