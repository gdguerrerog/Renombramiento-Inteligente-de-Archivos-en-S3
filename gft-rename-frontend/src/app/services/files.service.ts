import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { FileWithStatus } from '../domain/fileWithStatus.interface';



@Injectable({
  providedIn: 'root'
})
export class FilesService {
  private readonly apiHost = environment.HOST;

  constructor(private http: HttpClient) {}

  getToRename(): Observable<FileWithStatus[]> {
    return this.fetchFiles('/api/file/to-rename');
  }

  getUnmatched(): Observable<FileWithStatus[]> {
    return this.fetchFiles('/api/file/unmatched');
  }

  getRenamed(): Observable<FileWithStatus[]> {
    return this.fetchFiles('/api/file/renamed');
  }

  private fetchFiles(endpoint: string): Observable<FileWithStatus[]> {
    const url = `${this.apiHost}${endpoint}`;
    return this.http.get<FileWithStatus[]>(url);
  }
}
