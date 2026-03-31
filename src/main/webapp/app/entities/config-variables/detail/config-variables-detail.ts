import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';

import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { IConfigVariables } from '../config-variables.model';

@Component({
  selector: 'jhi-config-variables-detail',
  templateUrl: './config-variables-detail.html',
  imports: [FontAwesomeModule, Alert, AlertError, TranslateDirective, TranslateModule, RouterLink],
})
export class ConfigVariablesDetail {
  readonly configVariables = input<IConfigVariables | null>(null);

  previousState(): void {
    globalThis.history.back();
  }
}
