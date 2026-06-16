import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Rule } from '../domain/rule.interface';


@Injectable({
  providedIn: 'root'
})
export class RulesService {
  private readonly apiHost = environment.HOST;

  constructor(private http: HttpClient) {}

  getRules(): Observable<Rule[]> {
    const url = `${this.apiHost}/api/rules`;
    return this.http.get<Rule[]>(url);
  }

  createRule(name: string, order: number, toCheck: string, toReplace: string, type: "PREFIX" | "SUFFIX"): Observable<Rule> {
    const url = `${this.apiHost}/api/rules`;
    return this.http.post<Rule>(url, { name, order, toCheck, toReplace, type });
  }

  updateRule(id: number, name: string, order: number): Observable<Rule> {
    const url = `${this.apiHost}/api/rules/${id}`;
    return this.http.put<Rule>(url, { name, order });
  }


}
