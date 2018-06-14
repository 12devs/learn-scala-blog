import {CommentEntity} from "./comment";

export class ArticleEntity {
  id: number;
  title: string;
  mainText: string;
  comments: CommentEntity[];

  public constructor() {
    this.id = -1;
    this.title = "";
    this.mainText = "";
    this.comments = null;
  }
}
