import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';

import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { IUrllink } from '../urllink.model';

@Component({
  selector: 'jhi-urllink-detail',
  templateUrl: './urllink-detail.html',
  imports: [FontAwesomeModule, Alert, AlertError, TranslateDirective, TranslateModule, RouterLink],
})
export class UrllinkDetail {
  readonly urllink = input<IUrllink | null>(null);

  previousState(): void {
    globalThis.history.back();
  }
}
