import axios from 'axios';

export interface IArticle {
    id: string;
    title: string;
    mainText: string;
}

export namespace ArticleService {
    export function getArticles(): Promise<IArticle[]> {
        return axios.get('/api/1/articles')
            .then(response => response.data as IArticle[]);
    }

    /* export function createUser(newUser: NewUser): Promise<User> {
         return axios.post('/api/1/user', newUser)
             .then(response => response.data as User);
     }

     export function updateUser(user: User): Promise<User> {
         return axios.put('/api/1/user', user)
             .then(response => response.data as User);
     }

     export function deleteUser(userId: string): Promise<void> {
         return axios.delete('/api/1/user/' + userId)
             .then(response => undefined);
     }*/
}
