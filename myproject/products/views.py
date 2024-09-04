from django.urls import path,include
from rest_framework.routers import DefaultRouter
from .views import productviewset

router=DefaultRouter()
router.register(r'products',productviewset)

urlpatterns=[
    path('',include(router.urls)),


]