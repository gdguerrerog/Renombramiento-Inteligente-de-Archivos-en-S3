import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CdkDragDrop, DragDropModule } from '@angular/cdk/drag-drop';
import { RulesService } from '../services/rules.service';
import { Rule } from '../domain/rule.interface';
import { combineLatest } from 'rxjs';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-rules',
  standalone: true,
  imports: [CommonModule, DragDropModule, FormsModule],
  templateUrl: './rules.component.html',
  styleUrls: ['./rules.component.scss']
})
export class RulesComponent implements OnInit {
  rules: Rule[] = [];
  error = '';

  // Modal state
  showModal = false;
  formData = {
    name: '',
    toCheck: '',
    toReplace: '',
    type: 'PREFIX' as 'PREFIX' | 'SUFFIX'
  };
  submitting = false;

  constructor(private rulesService: RulesService) {}

  ngOnInit(): void {
    this.loadRules();
  }

  private loadRules() {
    this.error = '';
    this.rulesService.getRules().subscribe(rules => {
      this.rules = rules.sort((a, b) => a.order - b.order);
    });
  }

  openModal() {
    this.showModal = true;
    this.resetForm();
  }

  closeModal() {
    this.showModal = false;
    this.resetForm();
  }

  private resetForm() {
    this.formData = {
      name: '',
      toCheck: '',
      toReplace: '',
      type: 'PREFIX'
    };
    this.submitting = false;
  }

  submitForm() {
    if (!this.formData.name || !this.formData.toCheck || !this.formData.toReplace) {
      alert('Please fill in all fields');
      return;
    }

    this.submitting = true;
    const order = this.rules.length;

    this.rulesService.createRule(
      this.formData.name,
      order,
      this.formData.toCheck,
      this.formData.toReplace,
      this.formData.type
    ).subscribe(
      (newRule) => {
        this.rules = [...this.rules, newRule].sort((a, b) => a.order - b.order);
        this.closeModal();
        this.submitting = false;
      },
    );
  }

  drop(event: CdkDragDrop<Rule[]>) {
    if (event.previousIndex !== event.currentIndex) {
      this.swap(event.previousIndex, event.currentIndex);
    }
  }

  private swap(index1: number, index2: number) {
    const movedRule = this.rules[index1];
    const currentRule = this.rules[index2];

    combineLatest([
      this.rulesService.updateRule(movedRule.id, movedRule.name, index2),
      this.rulesService.updateRule(currentRule.id, currentRule.name, index1)
    ]).subscribe(([newMovedRule, newCurrentRule]) => {
      this.rules[index2] = newMovedRule;
      this.rules[index1] = newCurrentRule;
      this.rules = [...this.rules].sort((a, b) => a.order - b.order);
    });
  }

  moveUp(index: number) {
    if (index > 0) {
      this.swap(index, index - 1);
    }
  }

  moveDown(index: number) {
    if (index < this.rules.length - 1) {
      this.swap(index, index + 1);
    }
  }

}

