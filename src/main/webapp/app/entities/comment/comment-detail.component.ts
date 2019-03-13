import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IComment } from 'app/shared/model/comment.model';

@Component({
  selector: 'jhi-comment-detail',
  templateUrl: './comment-detail.component.html'
})
export class CommentDetailComponent implements OnInit {
  comment: IComment;

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.data.subscribe(({ comment }) => {
      this.comment = comment.body ? comment.body : comment;
    });
  }

  previousState() {
    window.history.back();
  }
}
