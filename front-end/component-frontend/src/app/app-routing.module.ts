import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RecommandationComponent } from './app/page/recommandation/recommandation.component';

const routes: Routes = [
  {path: 'recommendation', component: RecommandationComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
