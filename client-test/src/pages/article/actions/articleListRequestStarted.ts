import { actionsEnums } from "../../../common/actionsEnums";
import { ArticleEntity } from "../../../model/article";
import { articleApi } from "../../../rest-api/article-api";
import { articleListRequestCompletedAction } from "./articleListRequestCompleted";

export const articleListRequestStartedAction = () => {
  return function(dispatcher) {
    const promise = articleApi.loadArticleList();

    promise.then(
      data => {
        dispatcher(articleListRequestCompletedAction(data));
      }
    );

    return promise;
  };
};
