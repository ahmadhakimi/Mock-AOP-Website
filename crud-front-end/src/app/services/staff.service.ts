import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Staff } from '../interfaces/staff';
import { Observable } from 'rxjs';
import { authInterceptor } from '../auth.interceptor';

@Injectable({
  providedIn: 'root',
})
export class StaffService {
  private staffUrl = 'http://localhost:8080/api/aop/staff';

  constructor(private http: HttpClient) {
    console.log('HttpClient instance:', http);
  }

  // get all staffs

  getStaffList(): Observable<Staff[]> {
    return this.http.get<Staff[]>(`${this.staffUrl}`);
  }

  // view a staff by id

  getViewStaff(id: string): Observable<Staff> {
    return this.http.get<Staff>(`${this.staffUrl}/${id}`);
  }

  // update staff by id

  updateStaff(id: string, staff: Staff): Observable<Staff> {
    return this.http.put<Staff>(`${this.staffUrl}/${id}`, staff);
  }

  // delete staff by id

  deleteStaff(id: string): Observable<Staff> {
    return this.http.delete<Staff>(`${this.staffUrl}/${id}`, {
      responseType: 'text' as 'json',
    });
  }
}
