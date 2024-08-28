import { AbstractControl, ValidatorFn } from '@angular/forms';

export function passwordLengthValidator(): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    const isValidLength = /^[a-zA-Z0-9]{6,}$/.test(control.value);
    return isValidLength ? null : { invalidLength: true };
  };
}

export function passwordCompositionValidator(): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    const hasLetterAndNumber = /^(?=.*[a-zA-Z])(?=.*[0-9])/.test(control.value);
    return hasLetterAndNumber ? null : { invalidComposition: true };
  };
}
