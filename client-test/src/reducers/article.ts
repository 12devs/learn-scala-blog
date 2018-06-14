import { actionsEnums } from "../common/actionsEnums";
import { ArticleEntity } from "../model/article";

class ArticleState  {
  articlesList: ArticleEntity[];

  public constructor() {
    this.articlesList = [];
  }
}

export const articleReducer =  (state: ArticleState = new ArticleState(), action) => {
  switch (action.type) {
    case actionsEnums.ARTICLES_GET_LIST_REQUEST_COMPLETED:
      return handleGetArticleList(state, action.payload);
  }

  return state;
};

const handleGetArticleList = (state: ArticleState, payload: ArticleEntity[]) => {
  return {
    ...state,
    articlesList: payload
  }
};
