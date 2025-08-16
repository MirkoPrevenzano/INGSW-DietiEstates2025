import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { ErrorResponse } from '../model/errorResponse';

@Injectable({
  providedIn: 'root'
})
export class HandleNotifyService {

  constructor(private readonly toastr: ToastrService) { }

  showMessageError(error: ErrorResponse): void {
    const title = error.title || 'Error';
    let message = error.detail || 'An error occurred';
    
    const details: string[] = [];
    
    if (error.subErrors && error.subErrors.length > 0) {
      details.push(...error.subErrors);
    }
    
    if (details.length > 0) {
      message += '\n\n' + details.join('\n');
    }

    const config = {
      timeOut: 4000,
      extendedTimeOut: 3000,
      closeButton: true,
      progressBar: true,
      tapToDismiss: true
    };

    if (error.status >= 400 && error.status < 500) {
      this.toastr.warning(message, title, config);
    } else if (error.status >= 500 && error.status < 600) {
      this.toastr.error(message, title, config);
    } else {
      this.toastr.error(message, 'Unexpected Error', config);
    }
  }

}
