import { ChangeDetectionStrategy, Component, OnInit, signal, WritableSignal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FilesService } from '../services/files.service';
import { FileWithStatus } from '../domain/fileWithStatus.interface';

@Component({
  selector: 'app-files-browser',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './files-browser.component.html',
  styleUrls: ['./files-browser.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FilesBrowserComponent implements OnInit {
  activeTab: 'toRename' | 'unmatched' | 'renamed' = 'toRename';

  toRename: WritableSignal<FileWithStatus[]> = signal([]);
  unmatched: WritableSignal<FileWithStatus[]> = signal([]);
  renamed: WritableSignal<FileWithStatus[]> = signal([]);

  expandedFileName: WritableSignal<string | null> = signal(null);

  loading = signal({ toRename: 'not-loaded', unmatched: 'not-loaded', renamed: 'not-loaded' });

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

  toggleFileExpansion(fileName: string) {
    this.expandedFileName.set(
      this.expandedFileName() === fileName ? null : fileName
    );
  }

  private async loadActiveTab() {
    this.loading.set({...this.loading(), [this.activeTab]: 'not-loaded'});
    if (this.activeTab === 'toRename') return this.loadToRename();
    if (this.activeTab === 'unmatched') return this.loadUnmatched();
    return this.loadRenamed();
  }

  private async loadToRename() {
    const key = 'toRename';
    if (this.loading()[key] == 'loaded') return;
    this.loading.set({...this.loading(), [key]: 'loading'});
    this.filesService.getToRename().subscribe((files) => {
      this.toRename.set(files);
      this.loading.set({...this.loading(), [key]: 'loaded'});
    })
  }

  private async loadUnmatched() {
    const key = 'unmatched';
    if (this.loading()[key] == 'loaded') return;
    this.loading.set({...this.loading(), [key]: 'loading'});
    this.filesService.getUnmatched().subscribe((files) => {
      this.unmatched.set(files);
      this.loading.set({...this.loading(), [key]: 'loaded'});
    })
  }

  private async loadRenamed() {
    const key = 'renamed';
    if (this.loading()[key] == 'loaded') return;
    this.loading.set({...this.loading(), [key]: 'loading'});
    this.filesService.getRenamed().subscribe((files) => {
      this.renamed.set(files);
      this.loading.set({...this.loading(), [key]: 'loaded'});
    })
  }
}
