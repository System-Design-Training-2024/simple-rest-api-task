from django.db import models

# Create your models here.

class Product(models.Model):
    #id = models.IntegerField(primary_key=True)
    name = models.CharField(max_length=50)
    description =  models.CharField(max_length=500)
    price = models.FloatField()
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)