import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function yearRangeValidator(): ValidatorFn {
  return (formGroup: AbstractControl): ValidationErrors | null => {
    const yearControl = formGroup.get('year');
    const eraControl = formGroup.get('era');

    if (!yearControl || !eraControl) {
      return null; // controls not found
    }

    const year = yearControl.value;
    const era = eraControl.value;

    if(!year){
      return {yearRange : 'year is empty'}
    }

    if (era === 'BCE' && (year < 1 || year > 4000)) {
      return { yearRange: 'For BCE, year must be between 1 and 4000' };
    }

    if (era === 'CE' && (year < 1 || year > new Date().getFullYear())) {
      return { yearRange: `For CE, year must be between 1 and ${new Date().getFullYear()}` };
    }

    return null; // validation passed
  };
}

export function priceValidator(): ValidatorFn {
  return (formGroup: AbstractControl): ValidationErrors | null => {
    const halfControl = formGroup.get('halfPrice');
    const oneControl = formGroup.get('onePrice');
    const oneHalfControl = formGroup.get('oneHalfPrice');

    if (!halfControl || !oneControl || !oneHalfControl) {
      return null; // controls not found
    }

    const half = halfControl.value;
    const one = oneControl.value;
    const oneHalf = oneHalfControl.value;

    if(!half){
      return {priceError : 'half is empty'}
    }

    if(!one){
      return {priceError : 'one is empty'}
    }

    if(!oneHalf){
      return {priceError : 'one half is empty'}
    }


    if(one < half || oneHalf < half || one > oneHalf)
      return {priceError : 'prices are not in order'}

    return null; // validation passed
  };
}


