import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';

import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { ICactivity } from '../cactivity.model';

@Component({
  selector: 'jhi-cactivity-detail',
  templateUrl: './cactivity-detail.html',
  imports: [FontAwesomeModule, Alert, AlertError, TranslateDirective, TranslateModule, RouterLink],
})
export class CactivityDetail {
  readonly cactivity = input<ICactivity | null>(null);

  previousState(): void {
    globalThis.history.back();
  }
}
