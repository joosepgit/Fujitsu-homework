import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {EntryService} from './entry.service';
import {HttpClientModule} from '@angular/common/http';
import {NgbModalModule, NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule} from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    NgbModalModule,
    NgbModule,
    FormsModule,
  ],
  providers: [EntryService],
  bootstrap: [AppComponent]
})
export class AppModule { }
