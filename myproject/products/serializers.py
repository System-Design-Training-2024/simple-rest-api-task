from rest_framework import serializers
from .models import Product

class productserializer(serializers.ModelSerializer):
    class meta:
        model=Product
        fields='__all__'


