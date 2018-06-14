import * as React from 'react'
import {Link} from 'react-router'

export const App = (props: { children? }) => {
    return (
        <div>
            <header>
                Links:
                {' '}
                <Link to="/">Login</Link>
                {' '}
                <Link to="/articles">Home</Link>
                {' '}
                <Link to="/article-list">Article List</Link>
            </header>
            <div style={{marginTop: '1.5em'}}>{props.children}</div>
        </div>
    )
}
