import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FilesService } from '../services/files.service';
import { FileWithStatus } from '../domain/fileWithStatus.interface';

@Component({
  selector: 'app-files-browser',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './files-browser.component.html',
  styleUrls: ['./files-browser.component.scss']
})
export class FilesBrowserComponent implements OnInit {
  activeTab: 'toRename' | 'unmatched' | 'renamed' = 'toRename';

  toRename: FileWithStatus[] = [];
  unmatched: FileWithStatus[] = [];
  renamed: FileWithStatus[] = [];



  loading = { toRename: 'not-loaded', unmatched: 'not-loaded', renamed: 'not-loaded' };
  error = { toRename: '', unmatched: '', renamed: '' };

  constructor(private filesService: FilesService) {}

  ngOnInit(): void {
    this.loadActiveTab();
  }

  setTab(tab: 'toRename' | 'unmatched' | 'renamed') {
    if (this.activeTab === tab) return;
    this.activeTab = tab;
    this.loadActiveTab();
  }

  refreshCurrentTab() {
    this.loadActiveTab();
  }

  private async loadActiveTab() {
    if (this.activeTab === 'toRename') return this.loadToRename();
    if (this.activeTab === 'unmatched') return this.loadUnmatched();
    return this.loadRenamed();
  }

  private async loadToRename() {
    const key = 'toRename';
    if (this.loading[key] == 'loaded') return;
    this.loading[key] = 'loading';
    this.error[key] = '';
    this.filesService.getToRename().subscribe((files) => {
      this.toRename = files;
      this.loading[key] = 'loaded';
    })
  }

  private async loadUnmatched() {
    const key = 'unmatched';
    if (this.loading[key] == 'loaded') return;
    this.loading[key] = 'loading';
    this.error[key] = '';
    this.filesService.getUnmatched().subscribe((files) => {
      this.unmatched = files;
      this.loading[key] = 'loaded';
    })
  }

  private async loadRenamed() {
    const key = 'renamed';
    if (this.loading[key] == 'loaded') return;
    this.loading[key] = 'loading';
    this.error[key] = '';
    this.filesService.getRenamed().subscribe((files) => {
      this.renamed = files;
      this.loading[key] = 'loaded';
    })
  }
}
