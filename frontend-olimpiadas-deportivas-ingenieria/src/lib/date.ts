import { format as dateFnsFormat, parse, isAfter, isBefore, isEqual } from 'date-fns';
import { es } from 'date-fns/locale';
import { toZonedTime, fromZonedTime } from 'date-fns-tz';

const TIMEZONE = 'America/Bogota';

export const formatDate = (date: Date | string, formatStr = 'dd/MM/yyyy'): string => {
  const dateObj = typeof date === 'string' ? new Date(date) : date;
  const zonedDate = toZonedTime(dateObj, TIMEZONE);
  return dateFnsFormat(zonedDate, formatStr, { locale: es });
};

export const formatDateTime = (date: Date | string): string => {
  return formatDate(date, 'dd/MM/yyyy HH:mm');
};

export const parseDate = (dateStr: string, formatStr = 'dd/MM/yyyy'): Date => {
  return parse(dateStr, formatStr, new Date(), { locale: es });
};

export const getCurrentDateInTimezone = (): Date => {
  return toZonedTime(new Date(), TIMEZONE);
};

export const isInPast = (date: Date | string): boolean => {
  const dateObj = typeof date === 'string' ? new Date(date) : date;
  const zonedDate = toZonedTime(dateObj, TIMEZONE);
  const now = getCurrentDateInTimezone();
  return isBefore(zonedDate, now);
};

export const toISOStringInTimezone = (date: Date): string => {
  return fromZonedTime(date, TIMEZONE).toISOString();
};

export const areDatesEqual = (date1: Date | string, date2: Date | string): boolean => {
  const d1 = typeof date1 === 'string' ? new Date(date1) : date1;
  const d2 = typeof date2 === 'string' ? new Date(date2) : date2;
  return isEqual(toZonedTime(d1, TIMEZONE), toZonedTime(d2, TIMEZONE));
};
