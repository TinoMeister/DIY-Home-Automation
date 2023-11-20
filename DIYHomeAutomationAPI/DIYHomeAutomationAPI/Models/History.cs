namespace DIYHomeAutomationAPI.Models
{
    /// <summary>
    /// This class represents a History
    /// </summary>
    public class History
    {
        /// <summary>
        /// History's Id
        /// </summary>
        public int Id { get; set; }

        /// <summary>
        /// Device's Value
        /// </summary>
        public double Value { get; set; }

        /// <summary>
        /// History's Date
        /// </summary>
        public DateTime Date { get; set; }

        /// <summary>
        /// Device's Id
        /// </summary>
        public int DeviceId { get; set; }
    }
}
