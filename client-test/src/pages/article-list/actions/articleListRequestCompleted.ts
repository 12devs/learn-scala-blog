import { actionsEnums } from "../../../common/actionsEnums";
import { ArticleEntity } from "../../../model/article";

export const articleListRequestCompletedAction = (articleList: ArticleEntity[]) => {
  return {
    type: actionsEnums.ARTICLES_GET_LIST_REQUEST_COMPLETED,
    payload: articleList
  };
};
