import pandas as pd
# Make display smaller
pd.options.display.max_rows = 10

unames = ['user_id', 'gender', 'age', 'occupation', 'zip']
users = pd.read_table('/home/desktop/NetBeansProjects/iaa2022/python/tarefa2/datasets/movielens/users.dat', sep='::',
                      header=None, names=unames)

rnames = ['user_id', 'movie_id', 'rating', 'timestamp']
ratings = pd.read_table('/home/desktop/NetBeansProjects/iaa2022/python/tarefa2/datasets/movielens/ratings.dat', sep='::',
                        header=None, names=rnames)

mnames = ['movie_id', 'title', 'genres']
movies = pd.read_table('/home/desktop/NetBeansProjects/iaa2022/python/tarefa2/datasets/movielens/movies.dat', sep='::',
                       header=None, names=mnames)
data = pd.merge(pd.merge(ratings, users), movies)

mean_ratings = data.pivot_table('rating', index='title',
                                 columns='gender', aggfunc='mean')

ratings_by_title = data.groupby('title').size()

ratings_by_title[:10]
active_titles = ratings_by_title.index[ratings_by_title >= 300]
active_titles

# Select rows on the index
mean_ratings = mean_ratings.loc[active_titles]

top_female_ratings = mean_ratings.sort_values(by='F', ascending=False)
top_female_ratings[:10]

mean_ratings['diff'] = mean_ratings['M'] - mean_ratings['F']

sorted_by_diff = mean_ratings.sort_values(by='diff')
sorted_by_diff[:10]

sorted_by_diff[::-1][:10]

# Standard deviation of rating grouped by title
rating_std_by_title = data.groupby('title')['rating'].std()
# Filter down to active_titles
rating_std_by_title = rating_std_by_title.loc[active_titles]
# Order Series by value in descending order
rating_std_by_title.sort_values(ascending=False)[:10]