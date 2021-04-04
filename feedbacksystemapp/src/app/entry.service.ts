import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Entry} from './entry';
import {environment} from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EntryService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getEntries(): Observable<Entry[]> {
    return this.http.get<Entry[]>(`${this.apiServerUrl}/entries`);
  }

  public addEntry(entry: Entry): Observable<Entry> {
    return this.http.post<Entry>(`${this.apiServerUrl}/entry`, entry);
  }

  public deleteEntry(entryId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/entry/${entryId}/delete`);
  }
}
