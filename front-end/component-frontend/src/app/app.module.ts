import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './app/navbar/navbar.component';
import { RecommandationComponent } from './app/page/recommandation/recommandation.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    RecommandationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
