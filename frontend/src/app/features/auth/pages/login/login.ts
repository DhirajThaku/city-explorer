import { Component, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  imports: [RouterLink, ReactiveFormsModule],
  selector: 'app-login',
  styleUrl: './login.css',
  templateUrl: './login.html',
})
export class Login {

  loginForm = new FormGroup({
    email: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.email]
    }),

    password: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required]
    })
  });

  //Signals for UI state
  loading = signal(false);
  errorMessage = signal('');
  onSubmit(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    //  this.loading.set(true);
    this.errorMessage.set('');

    //API integration will be implemented later.
    console.log("Login form data:", this.loginForm.value);
  }
}
