import { AfterViewInit, ChangeDetectionStrategy, Component, OnInit, signal, WritableSignal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RulesService } from '../services/rules.service';
import { Rule } from '../domain/rule.interface';
import { combineLatest } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { RulesForm } from '../rules-form/rules-form';

@Component({
  selector: 'app-rules',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './rules.component.html',
  styleUrls: ['./rules.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class RulesComponent implements AfterViewInit {
  rules: WritableSignal<Rule[]> = signal([]);

  constructor(private rulesService: RulesService, private dialog: MatDialog) {}

  ngAfterViewInit(): void {
    this.loadRules();
  }


  private loadRules() {
    this.rulesService.getRules().subscribe(rules => {
      this.rules.set(rules.sort((a, b) => a.order - b.order));
    });
  }

  private swap(index1: number, index2: number) {
    const movedRule = this.rules()[index1];
    const currentRule = this.rules()[index2];

    combineLatest([
      this.rulesService.updateRule(movedRule.id, movedRule.name, index2),
      this.rulesService.updateRule(currentRule.id, currentRule.name, index1)
    ]).subscribe(([newMovedRule, newCurrentRule]) => {
      this.rules.set(this.rules().map((rule, i) => {
        if (i === index1) return newCurrentRule;
        if (i === index2) return newMovedRule;
        return rule;
      }).sort((a, b) => a.order - b.order));
    });
  }

  moveUp(index: number) {
    if (index > 0) {
      this.swap(index, index - 1);
    }
  }

  moveDown(index: number) {
    if (index < this.rules().length - 1) {
      this.swap(index, index + 1);
    }
  }

  openCreateRule() {
    this.dialog.open(RulesForm).afterClosed().subscribe(result => {
      if (!result) return;
      this.rulesService.createRule(result.name, this.rules().length, result.toCheck, result.toReplace, result.type).subscribe((rule) => {
        this.rules.set([...this.rules(), rule].sort((a, b) => a.order - b.order))
      })
    })
  }

}

