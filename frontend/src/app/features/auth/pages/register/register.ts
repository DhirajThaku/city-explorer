import { Component, signal } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  imports: [RouterLink, ReactiveFormsModule],
  selector: 'app-register',
  styleUrl: './register.css',
  templateUrl: './register.html',
})
export class Register {

  //Custom validator for matching passwords
  passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
    const password = control.get('password')?.value;
    const confirmPassword = control.get('confirmPassword')?.value;

    if (password !== confirmPassword) {
      return { passwordMismatch: true };
    }
    return null;
  }

  //Reactive registration form
  registerForm = new FormGroup(
    {
      name: new FormControl('', {
        nonNullable: true,
        validators: [Validators.required]
      }),
      email: new FormControl('', {
        nonNullable: true,
        validators: [Validators.required, Validators.email]
      }),

      password: new FormControl('', {
        nonNullable: true,
        validators: [
          Validators.required,
          Validators.minLength(8)
        ]
      }),

      confirmPassword: new FormControl('', {
        nonNullable: true,
        validators: [Validators.required]
      })
    },
    {
      validators: [this.passwordMatchValidator]
    }
  );

  //signals for UI state
  loading = signal(false);
  errorMessage = signal('');
  successMessage = signal('');


  onSubmit(): void {
    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return;
    }
    this.errorMessage.set('');
    this.successMessage.set('');

    console.log('Registration form data:', this.registerForm.value);
  }
}
