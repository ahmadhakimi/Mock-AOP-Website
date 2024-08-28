import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PassService {
  private apiUrl = 'http://localhost:8080/api/aop/pass';

  constructor(private http: HttpClient) {
    console.log('HttpClient instance:', http);
  }

  createPass(pass: FormData, attachment: File): Observable<any> {
    const formData = new FormData();
    formData.append('pass', JSON.stringify(pass));
    formData.append('attachment', attachment);
    return this.http.post<any>(`${this.apiUrl}`, formData);
  }
}
