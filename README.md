# DeezerAlbumsFetch
DeezerAlbumsFetch


In a nutshell, this project describe how to use below features without using external (3rd) libraries. 
- DiskLruCache - FileSystem Cache (Persist on a App kill).
- Image LruCache - Mem Cache to store and reload images. <br>
(Check out the regex to be used as a cache key - Not based on API URL) -- AlbumUtil.getCoverId
- PriorityThreadPoolExecutor - A ThreadPoolExecutor implementation. Manage a group of tasks based on availables processor and tasks prioritization. 
- Image View Downloader - A ImageView with a placeholder while the defined URL image is being downloaded. I've also added an animation to displays the image when it get's loaded. 
- API Request -  A basic network request using Async Task.
- Dropdown button - Drop down list for a button. Used to choose the cache type.

Demo: 

![ConfigTab](https://github.com/febaisi/DeezerAlbumsFetch/blob/master/demo/menu.gif).  .   .![AlbumList](https://github.com/febaisi/DeezerAlbumsFetch/blob/master/demo/albumlist.gif)
