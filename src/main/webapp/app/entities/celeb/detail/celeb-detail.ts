import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';

import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { ICeleb } from '../celeb.model';

@Component({
  selector: 'jhi-celeb-detail',
  templateUrl: './celeb-detail.html',
  imports: [FontAwesomeModule, Alert, AlertError, TranslateDirective, TranslateModule, RouterLink],
})
export class CelebDetail {
  readonly celeb = input<ICeleb | null>(null);

  previousState(): void {
    globalThis.history.back();
  }
}
