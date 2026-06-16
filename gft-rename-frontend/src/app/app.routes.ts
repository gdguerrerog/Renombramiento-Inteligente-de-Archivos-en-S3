import { Routes } from '@angular/router';
import { FilesBrowserComponent } from './files-browser/files-browser.component';
import { RulesComponent } from './rules/rules.component';

export const routes: Routes = [
  { path: '', redirectTo: 'files', pathMatch: 'full' },
  { path: 'files', component: FilesBrowserComponent },
  { path: 'rules', component: RulesComponent }
];
