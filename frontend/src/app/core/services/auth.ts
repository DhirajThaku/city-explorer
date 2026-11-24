import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { LoginRequest, LoginResponse, RegisterRequest, RegisterResponse } from './models/auth.model';
import { environment } from '../../../enviroments/enviroment';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    private http = inject(HttpClient);


    register(request: RegisterRequest): Observable<RegisterResponse> {

        return this.http.post<RegisterResponse>(
            `${environment.apiUrl}/auth/register`, request
        );
    }

    login(request: LoginRequest): Observable<LoginResponse> {
        return this.http.post<LoginResponse>(
            `${environment.apiUrl}/auth/login`, request
        )
    }
}
