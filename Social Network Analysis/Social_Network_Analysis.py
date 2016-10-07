"""

Collecting a political social network

"""

from collections import Counter
import matplotlib.pyplot as plt
import networkx as nx
import sys
import time
from TwitterAPI import TwitterAPI

consumer_key = '49AjHWfcsUSYESVzH0gnZ5cHZ'
consumer_secret = 'CNm8vKtl3tn99FI3jSWoomH74AYlasYX2Ht3Xa2nPdxLyJfSZm'
access_token = '1423944660-f7u0ovEn6ZffhUG6ce3SwD4XfHJpnGYDSXHOiGQ'
access_token_secret = 'zhAMb0GL3OQogbZSsxDLlZJSaCDD1TGR7dlkp9OX7wUvJ'


# This method is done for you. Make sure to put your credentials in the file twitter.cfg.
def get_twitter():
    """ Construct an instance of TwitterAPI using the tokens you entered above.
    Returns:
      An instance of TwitterAPI.
    """
    return TwitterAPI(consumer_key, consumer_secret, access_token, access_token_secret)


def read_screen_names(filename):
    """
    Read a text file containing Twitter screen_names, one per line.
    Params:
        filename....Name of the file to read.
    Returns:
        A list of strings, one per screen_name, in the order they are listed
        in the file.
    Here's a doctest to confirm your implementation is correct.
    >>> read_screen_names('candidates.txt')
    ['DrJillStein', 'GovGaryJohnson', 'HillaryClinton', 'realDonaldTrump']
    """
    flist = []
    f = open('candidates.txt')
    for line in f:
    	flist.append(line.strip('\n'))	
    return flist
    
# I've provided the method below to handle Twitter's rate limiting.
# You should call this method whenever you need to access the Twitter API.
def robust_request(twitter, resource, params, max_tries=5):


    """ If a Twitter request fails, sleep for 15 minutes.
    Do this at most max_tries times before quitting.
    Args:
      twitter .... A TwitterAPI object.
      resource ... A resource string to request; e.g., "friends/ids"
      params ..... A parameter dict for the request, e.g., to specify
                   parameters like screen_name or count.
      max_tries .. The maximum number of tries to attempt.
    Returns:
      A TwitterResponse object, or None if failed.
    """
    for i in range(max_tries):
        request = twitter.request(resource, params)
        if request.status_code == 200:
            return request
        else:
            print('Got error %s \nsleeping for 15 minutes.' % request.text)
            sys.stderr.flush()
            time.sleep(61 * 15)


def get_users(twitter, screen_names):
    """Retrieve the Twitter user objects for each screen_name.
    Params:
        twitter........The TwitterAPI object.
        screen_names...A list of strings, one per screen_name
    Returns:
        A list of dicts, one per user, containing all the user information
        (e.g., screen_name, id, location, etc)
    """
    request = robust_request(twitter, 'users/lookup', {'screen_name': screen_names}, max_tries=5)
    user_info = []
    for user in request:
    	user_info.append(user)
    return user_info	


def get_friends(twitter, screen_name):
    """ Return a list of Twitter IDs for users that this person follows, up to 5000.
    Note, because of rate limits, it's best to test this method for one candidate before trying
    on all candidates.
    Args:
        twitter.......The TwitterAPI object
        screen_name... a string of a Twitter screen name
    Returns:
        A list of ints, one per friend ID, sorted in ascending order.
    """
    request = robust_request(twitter, 'friends/ids', {'screen_name': screen_name}, max_tries=5)
    friend_list = []
    for r in request:
        friend_list.append(r)
    return sorted(friend_list)

def add_all_friends(twitter, users):
    """ Get the list of accounts each user follows.
    I.e., call the get_friends method for all 4 candidates.
    Store the result in each user's dict using a new key called 'friends'.
    Args:
        twitter...The TwitterAPI object.
        users.....The list of user dicts.
    Returns:
        Nothing
    """
    for u_dict in users:
        u_dict['friends'] = get_friends(twitter,u_dict['screen_name'])

def print_num_friends(users):
    """Print the number of friends per candidate, sorted by candidate name.
    Args:
        users....The list of user dicts.
    Returns:
        Nothing
    """
    for u_dict in users:
        print ("%s %d" %(u_dict['screen_name'], len(u_dict['friends'])))
		
def count_friends(users):
     """ Count how often each friend is followed.
    Args:
        users: a list of user dicts
    Returns:
        a Counter object mapping each friend to the number of candidates who follow them.
        Counter documentation: https://docs.python.org/dev/library/collections.html#collections.Counter
    """
     all_friends=[]
     for u_dict in users:
         for items in u_dict['friends']:
             all_friends.append(items)
     count = Counter()
     for frnd in all_friends:
         count[frnd]+=1
     return count
	

def friend_overlap(users):
    """
    Compute the number of shared accounts followed by each pair of users.
    Args:
        users...The list of user dicts.
    Return: A list of tuples containing (user1, user2, N), where N is the
        number of accounts that both user1 and user2 follow.  This list should
        be sorted in descending order of N. Ties are broken first by user1's
        screen_name, then by user2's screen_name (sorted in ascending
        alphabetical order). See Python's builtin sorted method.
    In this example, users 'a' and 'c' follow the same 3 accounts:
    """
    list_overlap = []
    list_common = []
    m=0
    for i in range(0,len(users)):
    	for j in range(i+1,len(users)):
    		s1 = set.intersection(set(users[i].get('friends')), set(users[j].get('friends')))
    		list_common.append(s1)
    for i in range(0,len(users)):
        for j in range(i+1,len(users)):
            list_overlap.append((users[i]['screen_name'],users[j]['screen_name'],len(list_common[m])))
            m = m + 1
    return sorted(list_overlap, key=lambda x: (x[2]), reverse=True)

def followed_by_hillary_and_donald(users, twitter):
    """
    Find and return the screen_name of the one Twitter user followed by both Hillary
    Clinton and Donald Trump. You will need to use the TwitterAPI to convert
    the Twitter ID to a screen_name. See:
    https://dev.twitter.com/rest/reference/get/users/lookup
    Params:
        users.....The list of user dicts
        twitter...The Twitter API object
    Returns:
        A string containing the single Twitter screen_name of the user
        that is followed by both Hillary Clinton and Donald Trump.
    """

    str = ''
    set1 = set()
    set2 = set()
    for u_dict in users:
    	if u_dict['screen_name'] == 'HillaryClinton':
    		set1 = set(u_dict['friends'])
    	elif u_dict['screen_name'] == 'realDonaldTrump':
    		set2 = set(u_dict['friends'])
    		
    common = set.intersection(set1, set2)
    request = robust_request(twitter, 'users/lookup', {'user_id': common}, max_tries=5)
    for user in request:
    	str = user['screen_name']	
    return str	
    	
def create_graph(users, friend_counts):
    list = []
    G = nx.Graph()
    for u_dict in users:
    	list.append(u_dict['screen_name'])
    for u in users:
    	for frnd in u['friends']:
            if friend_counts[frnd] > 1:
                G.add_edge(u['screen_name'],frnd)
    return G

def draw_network(graph, users, filename):
    labels = {}
    fig = plt.figure()
    for node in graph.nodes():
    	if node == 'DrJillStein' or node =='GovGaryJohnson' or node == 'HillaryClinton' or node == 'realDonaldTrump':
    		labels[node] = node
    nx.draw_networkx(graph,pos=nx.spring_layout(graph), labels=labels ,font_size= 10, node_size=40, node_color='red', width=0.06, alpha=0.5)
    plt.axis('off')
    plt.savefig(filename)
     
def main():
    """ Main method. You should not modify this. """
    twitter = get_twitter()
    screen_names = read_screen_names('candidates.txt')
    print('Established Twitter connection.')
    print('Read screen names: %s' % screen_names)
    users = sorted(get_users(twitter, screen_names), key=lambda x: x['screen_name'])
    print('found %d users with screen_names %s' %
          (len(users), str([u['screen_name'] for u in users])))
    add_all_friends(twitter, users)
    print('Friends per candidate:')
    print_num_friends(users)
    friend_counts = count_friends(users)
    print('Most common friends:\n%s' % str(friend_counts.most_common(5)))
    print('Friend Overlap:\n%s' % str(friend_overlap(users)))
    print('User followed by Hillary and Donald: %s' % followed_by_hillary_and_donald(users, twitter))
    graph = create_graph(users, friend_counts)
    print('graph has %s nodes and %s edges' % (len(graph.nodes()), len(graph.edges())))
    draw_network(graph, users, 'network.png')
    print('network drawn to network.png')
	

if __name__ == '__main__':
    main()
