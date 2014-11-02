from django.conf.urls import patterns, include, url
from django.contrib import admin
#import django_cron

#django_cron.autodiscover()

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'fft.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),
    
    url(r'^admin/', include(admin.site.urls)),
    url(r'^forum/', include('django_simple_forum.urls')),    
)
