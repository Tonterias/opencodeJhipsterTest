import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IFrontpageconfig, NewFrontpageconfig } from '../frontpageconfig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFrontpageconfig for edit and NewFrontpageconfigFormGroupInput for create.
 */
type FrontpageconfigFormGroupInput = IFrontpageconfig | PartialWithRequiredKeyOf<NewFrontpageconfig>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IFrontpageconfig | NewFrontpageconfig> = Omit<T, 'creationDate'> & {
  creationDate?: string | null;
};

type FrontpageconfigFormRawValue = FormValueOf<IFrontpageconfig>;

type NewFrontpageconfigFormRawValue = FormValueOf<NewFrontpageconfig>;

type FrontpageconfigFormDefaults = Pick<NewFrontpageconfig, 'id' | 'creationDate'>;

type FrontpageconfigFormGroupContent = {
  id: FormControl<FrontpageconfigFormRawValue['id'] | NewFrontpageconfig['id']>;
  creationDate: FormControl<FrontpageconfigFormRawValue['creationDate']>;
  topNews1: FormControl<FrontpageconfigFormRawValue['topNews1']>;
  topNews2: FormControl<FrontpageconfigFormRawValue['topNews2']>;
  topNews3: FormControl<FrontpageconfigFormRawValue['topNews3']>;
  topNews4: FormControl<FrontpageconfigFormRawValue['topNews4']>;
  topNews5: FormControl<FrontpageconfigFormRawValue['topNews5']>;
  latestNews1: FormControl<FrontpageconfigFormRawValue['latestNews1']>;
  latestNews2: FormControl<FrontpageconfigFormRawValue['latestNews2']>;
  latestNews3: FormControl<FrontpageconfigFormRawValue['latestNews3']>;
  latestNews4: FormControl<FrontpageconfigFormRawValue['latestNews4']>;
  latestNews5: FormControl<FrontpageconfigFormRawValue['latestNews5']>;
  breakingNews1: FormControl<FrontpageconfigFormRawValue['breakingNews1']>;
  recentPosts1: FormControl<FrontpageconfigFormRawValue['recentPosts1']>;
  recentPosts2: FormControl<FrontpageconfigFormRawValue['recentPosts2']>;
  recentPosts3: FormControl<FrontpageconfigFormRawValue['recentPosts3']>;
  recentPosts4: FormControl<FrontpageconfigFormRawValue['recentPosts4']>;
  featuredArticles1: FormControl<FrontpageconfigFormRawValue['featuredArticles1']>;
  featuredArticles2: FormControl<FrontpageconfigFormRawValue['featuredArticles2']>;
  featuredArticles3: FormControl<FrontpageconfigFormRawValue['featuredArticles3']>;
  featuredArticles4: FormControl<FrontpageconfigFormRawValue['featuredArticles4']>;
  featuredArticles5: FormControl<FrontpageconfigFormRawValue['featuredArticles5']>;
  featuredArticles6: FormControl<FrontpageconfigFormRawValue['featuredArticles6']>;
  featuredArticles7: FormControl<FrontpageconfigFormRawValue['featuredArticles7']>;
  featuredArticles8: FormControl<FrontpageconfigFormRawValue['featuredArticles8']>;
  featuredArticles9: FormControl<FrontpageconfigFormRawValue['featuredArticles9']>;
  featuredArticles10: FormControl<FrontpageconfigFormRawValue['featuredArticles10']>;
  popularNews1: FormControl<FrontpageconfigFormRawValue['popularNews1']>;
  popularNews2: FormControl<FrontpageconfigFormRawValue['popularNews2']>;
  popularNews3: FormControl<FrontpageconfigFormRawValue['popularNews3']>;
  popularNews4: FormControl<FrontpageconfigFormRawValue['popularNews4']>;
  popularNews5: FormControl<FrontpageconfigFormRawValue['popularNews5']>;
  popularNews6: FormControl<FrontpageconfigFormRawValue['popularNews6']>;
  popularNews7: FormControl<FrontpageconfigFormRawValue['popularNews7']>;
  popularNews8: FormControl<FrontpageconfigFormRawValue['popularNews8']>;
  weeklyNews1: FormControl<FrontpageconfigFormRawValue['weeklyNews1']>;
  weeklyNews2: FormControl<FrontpageconfigFormRawValue['weeklyNews2']>;
  weeklyNews3: FormControl<FrontpageconfigFormRawValue['weeklyNews3']>;
  weeklyNews4: FormControl<FrontpageconfigFormRawValue['weeklyNews4']>;
  newsFeeds1: FormControl<FrontpageconfigFormRawValue['newsFeeds1']>;
  newsFeeds2: FormControl<FrontpageconfigFormRawValue['newsFeeds2']>;
  newsFeeds3: FormControl<FrontpageconfigFormRawValue['newsFeeds3']>;
  newsFeeds4: FormControl<FrontpageconfigFormRawValue['newsFeeds4']>;
  newsFeeds5: FormControl<FrontpageconfigFormRawValue['newsFeeds5']>;
  newsFeeds6: FormControl<FrontpageconfigFormRawValue['newsFeeds6']>;
  usefulLinks1: FormControl<FrontpageconfigFormRawValue['usefulLinks1']>;
  usefulLinks2: FormControl<FrontpageconfigFormRawValue['usefulLinks2']>;
  usefulLinks3: FormControl<FrontpageconfigFormRawValue['usefulLinks3']>;
  usefulLinks4: FormControl<FrontpageconfigFormRawValue['usefulLinks4']>;
  usefulLinks5: FormControl<FrontpageconfigFormRawValue['usefulLinks5']>;
  usefulLinks6: FormControl<FrontpageconfigFormRawValue['usefulLinks6']>;
  recentVideos1: FormControl<FrontpageconfigFormRawValue['recentVideos1']>;
  recentVideos2: FormControl<FrontpageconfigFormRawValue['recentVideos2']>;
  recentVideos3: FormControl<FrontpageconfigFormRawValue['recentVideos3']>;
  recentVideos4: FormControl<FrontpageconfigFormRawValue['recentVideos4']>;
  recentVideos5: FormControl<FrontpageconfigFormRawValue['recentVideos5']>;
  recentVideos6: FormControl<FrontpageconfigFormRawValue['recentVideos6']>;
};

export type FrontpageconfigFormGroup = FormGroup<FrontpageconfigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FrontpageconfigFormService {
  createFrontpageconfigFormGroup(frontpageconfig?: FrontpageconfigFormGroupInput): FrontpageconfigFormGroup {
    const frontpageconfigRawValue = this.convertFrontpageconfigToFrontpageconfigRawValue({
      ...this.getFormDefaults(),
      ...(frontpageconfig ?? { id: null }),
    });
    return new FormGroup<FrontpageconfigFormGroupContent>({
      id: new FormControl(
        { value: frontpageconfigRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      creationDate: new FormControl(frontpageconfigRawValue.creationDate, {
        validators: [Validators.required],
      }),
      topNews1: new FormControl(frontpageconfigRawValue.topNews1),
      topNews2: new FormControl(frontpageconfigRawValue.topNews2),
      topNews3: new FormControl(frontpageconfigRawValue.topNews3),
      topNews4: new FormControl(frontpageconfigRawValue.topNews4),
      topNews5: new FormControl(frontpageconfigRawValue.topNews5),
      latestNews1: new FormControl(frontpageconfigRawValue.latestNews1),
      latestNews2: new FormControl(frontpageconfigRawValue.latestNews2),
      latestNews3: new FormControl(frontpageconfigRawValue.latestNews3),
      latestNews4: new FormControl(frontpageconfigRawValue.latestNews4),
      latestNews5: new FormControl(frontpageconfigRawValue.latestNews5),
      breakingNews1: new FormControl(frontpageconfigRawValue.breakingNews1),
      recentPosts1: new FormControl(frontpageconfigRawValue.recentPosts1),
      recentPosts2: new FormControl(frontpageconfigRawValue.recentPosts2),
      recentPosts3: new FormControl(frontpageconfigRawValue.recentPosts3),
      recentPosts4: new FormControl(frontpageconfigRawValue.recentPosts4),
      featuredArticles1: new FormControl(frontpageconfigRawValue.featuredArticles1),
      featuredArticles2: new FormControl(frontpageconfigRawValue.featuredArticles2),
      featuredArticles3: new FormControl(frontpageconfigRawValue.featuredArticles3),
      featuredArticles4: new FormControl(frontpageconfigRawValue.featuredArticles4),
      featuredArticles5: new FormControl(frontpageconfigRawValue.featuredArticles5),
      featuredArticles6: new FormControl(frontpageconfigRawValue.featuredArticles6),
      featuredArticles7: new FormControl(frontpageconfigRawValue.featuredArticles7),
      featuredArticles8: new FormControl(frontpageconfigRawValue.featuredArticles8),
      featuredArticles9: new FormControl(frontpageconfigRawValue.featuredArticles9),
      featuredArticles10: new FormControl(frontpageconfigRawValue.featuredArticles10),
      popularNews1: new FormControl(frontpageconfigRawValue.popularNews1),
      popularNews2: new FormControl(frontpageconfigRawValue.popularNews2),
      popularNews3: new FormControl(frontpageconfigRawValue.popularNews3),
      popularNews4: new FormControl(frontpageconfigRawValue.popularNews4),
      popularNews5: new FormControl(frontpageconfigRawValue.popularNews5),
      popularNews6: new FormControl(frontpageconfigRawValue.popularNews6),
      popularNews7: new FormControl(frontpageconfigRawValue.popularNews7),
      popularNews8: new FormControl(frontpageconfigRawValue.popularNews8),
      weeklyNews1: new FormControl(frontpageconfigRawValue.weeklyNews1),
      weeklyNews2: new FormControl(frontpageconfigRawValue.weeklyNews2),
      weeklyNews3: new FormControl(frontpageconfigRawValue.weeklyNews3),
      weeklyNews4: new FormControl(frontpageconfigRawValue.weeklyNews4),
      newsFeeds1: new FormControl(frontpageconfigRawValue.newsFeeds1),
      newsFeeds2: new FormControl(frontpageconfigRawValue.newsFeeds2),
      newsFeeds3: new FormControl(frontpageconfigRawValue.newsFeeds3),
      newsFeeds4: new FormControl(frontpageconfigRawValue.newsFeeds4),
      newsFeeds5: new FormControl(frontpageconfigRawValue.newsFeeds5),
      newsFeeds6: new FormControl(frontpageconfigRawValue.newsFeeds6),
      usefulLinks1: new FormControl(frontpageconfigRawValue.usefulLinks1),
      usefulLinks2: new FormControl(frontpageconfigRawValue.usefulLinks2),
      usefulLinks3: new FormControl(frontpageconfigRawValue.usefulLinks3),
      usefulLinks4: new FormControl(frontpageconfigRawValue.usefulLinks4),
      usefulLinks5: new FormControl(frontpageconfigRawValue.usefulLinks5),
      usefulLinks6: new FormControl(frontpageconfigRawValue.usefulLinks6),
      recentVideos1: new FormControl(frontpageconfigRawValue.recentVideos1),
      recentVideos2: new FormControl(frontpageconfigRawValue.recentVideos2),
      recentVideos3: new FormControl(frontpageconfigRawValue.recentVideos3),
      recentVideos4: new FormControl(frontpageconfigRawValue.recentVideos4),
      recentVideos5: new FormControl(frontpageconfigRawValue.recentVideos5),
      recentVideos6: new FormControl(frontpageconfigRawValue.recentVideos6),
    });
  }

  getFrontpageconfig(form: FrontpageconfigFormGroup): IFrontpageconfig | NewFrontpageconfig {
    return this.convertFrontpageconfigRawValueToFrontpageconfig(
      form.getRawValue() as FrontpageconfigFormRawValue | NewFrontpageconfigFormRawValue,
    );
  }

  resetForm(form: FrontpageconfigFormGroup, frontpageconfig: FrontpageconfigFormGroupInput): void {
    const frontpageconfigRawValue = this.convertFrontpageconfigToFrontpageconfigRawValue({ ...this.getFormDefaults(), ...frontpageconfig });
    form.reset({
      ...frontpageconfigRawValue,
      id: { value: frontpageconfigRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): FrontpageconfigFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      creationDate: currentTime,
    };
  }

  private convertFrontpageconfigRawValueToFrontpageconfig(
    rawFrontpageconfig: FrontpageconfigFormRawValue | NewFrontpageconfigFormRawValue,
  ): IFrontpageconfig | NewFrontpageconfig {
    return {
      ...rawFrontpageconfig,
      creationDate: dayjs(rawFrontpageconfig.creationDate, DATE_TIME_FORMAT),
    };
  }

  private convertFrontpageconfigToFrontpageconfigRawValue(
    frontpageconfig: IFrontpageconfig | (Partial<NewFrontpageconfig> & FrontpageconfigFormDefaults),
  ): FrontpageconfigFormRawValue | PartialWithRequiredKeyOf<NewFrontpageconfigFormRawValue> {
    return {
      ...frontpageconfig,
      creationDate: frontpageconfig.creationDate ? frontpageconfig.creationDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
