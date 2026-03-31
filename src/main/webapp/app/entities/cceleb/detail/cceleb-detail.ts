import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';

import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { ICceleb } from '../cceleb.model';

@Component({
  selector: 'jhi-cceleb-detail',
  templateUrl: './cceleb-detail.html',
  imports: [FontAwesomeModule, Alert, AlertError, TranslateDirective, TranslateModule, RouterLink],
})
export class CcelebDetail {
  readonly cceleb = input<ICceleb | null>(null);

  previousState(): void {
    globalThis.history.back();
  }
}
