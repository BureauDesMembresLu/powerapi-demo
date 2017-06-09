FROM php:7.1.5-apache
COPY src/ /var/www/html/
RUN pecl install mongodb \
  && docker-php-ext-enable mongodb